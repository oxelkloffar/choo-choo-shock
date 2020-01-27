const cdk = require('@aws-cdk/core')
const ec2 = require("@aws-cdk/aws-ec2")
const ecs = require("@aws-cdk/aws-ecs")
const ecs_patterns = require("@aws-cdk/aws-ecs-patterns")
const cert = require("@aws-cdk/aws-certificatemanager")
const route53 = require("@aws-cdk/aws-route53")
const path = require('path')

class ChooChooStack extends cdk.Stack {

  vpc
  cluster

  hostedZone
  backendDomainName
  frontendDomainName
  backendCertificate
  frontendCertificate

  backendService
  frontendService

  /**
   * @param {cdk.Construct} scope
   * @param {string} id
   * @param {cdk.StackProps=} props
   */
  constructor(scope, id, props) {
    super(scope, id, {
      env: {
        account: process.env.AWS_ACCOUNT_ID,
        region: process.env.AWS_DEFAULT_REGION
      },
      ...props
    })

    this.initOrchestratedCluster()
    this.initCertificates()

    this.initBackendService()
    this.initFrontendService()
  }

  initOrchestratedCluster() {
    this.vpc = new ec2.Vpc(this, "ChooVpc", {
      maxAzs: 3 // Default is all AZs in region
    })

    this.cluster = new ecs.Cluster(this, "ChooCluster", {
      vpc: this.vpc
    })
  }

  initCertificates() {
    const domainName = process.env.DNS_DOMAIN || "aourell.se"
    this.backendDomainName = (process.env.DNS_BACKEND_URL_PREFIX || "api.choo") + "." + domainName
    this.frontendDomainName = (process.env.DNS_FRONTEND_URL_PREFIX || "choo") + "." + domainName

    this.hostedZone = route53.HostedZone.fromLookup(this, "ChooDomain", {
      domainName
    })

    this.backendCertificate = new cert.DnsValidatedCertificate(this, "ChooBackendCert", {
      domainName: this.backendDomainName,
      hostedZone: this.hostedZone,
      validationMethod: cert.ValidationMethod.DNS
    })

    this.frontendCertificate = new cert.DnsValidatedCertificate(this, "ChooFrontendCert", {
      domainName: this.frontendDomainName,
      hostedZone: this.hostedZone,
      validationMethod: cert.ValidationMethod.DNS
    })
  }

  initBackendService() {
    // Config params for backend service
    const cpuUnitsOf1024 = +(process.env.SVC_BACKEND_VCPU || 256)
    const memoryLimitMiB = +(process.env.SVC_BACKEND_RAM || 512)
    const loadBalancedInstances = +(process.env.SVC_BACKEND_INSTANCE_COUNT || 1)
    const exposedContainerPort = +(process.env.SVC_BACKEND_CONTAINER_PORT || 8080)

    // Create a load-balanced Fargate service and make it public
    this.backendService = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "ChooBackendFargateService", {
      cluster: this.cluster,
      cpu: cpuUnitsOf1024, // Default is 256
      memoryLimitMiB: memoryLimitMiB, // Default is 512
      desiredCount: loadBalancedInstances, // Default is 1
      taskImageOptions: {
        image: ecs.ContainerImage.fromAsset(path.resolve(__dirname, '../../choo-backend')),
        containerPort: exposedContainerPort
      },
      publicLoadBalancer: true, // Default is false
      domainName: this.backendDomainName,
      domainZone: this.hostedZone,
      certificate: this.backendCertificate
    })
  }

  initFrontendService() {
    // Config params for frontend service
    const cpuUnitsOf1024 = +(process.env.SVC_FRONTEND_VCPU || 256)
    const memoryLimitMiB = +(process.env.SVC_FRONTEND_RAM || 512)
    const loadBalancedInstances = +(process.env.SVC_FRONTEND_INSTANCE_COUNT || 1)
    const exposedContainerPort = +(process.env.SVC_FRONTEND_CONTAINER_PORT || 5000)

    // Create a load-balanced Fargate service and make it public
    this.frontendService = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "ChooFrontendFargateService", {
      cluster: this.cluster,
      cpu: cpuUnitsOf1024, // Default is 256
      memoryLimitMiB: memoryLimitMiB, // Default is 512
      desiredCount: loadBalancedInstances, // Default is 1
      taskImageOptions: {
        image: ecs.ContainerImage.fromAsset(path.resolve(__dirname, '../../choo-frontend')),
        containerPort: exposedContainerPort
      },
      publicLoadBalancer: true, // Default is false
      domainName: this.frontendDomainName,
      domainZone: this.hostedZone,
      certificate: this.frontendCertificate
    })
  }
}

module.exports = { ChooChooStack }
