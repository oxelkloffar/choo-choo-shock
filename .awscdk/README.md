# Introduction

This is deployment configuration using the AWS Cloud Development Kit (CDK) IaC framework.


## Deployment

Before deploying, you need to configure CDK for your AWS account. Make sure you have an access key with sufficient access rights to create ECS Clusters, Security Groups, TLS Certificates, and configure Route53 entries for your chosen Hosted Zone.

Start by setting the following environment variables accordingly:
 * `AWS_ACCOUNT_ID`
 * `AWS_DEFAULT_REGION`
 * `AWS_ACCESS_KEY_ID`
 * `AWS_SECRET_ACCESS_KEY`

Next, set the following environment variables to configure the actual deployment:
 * `DNS_DOMAIN`, the domain name (and Hosted Zone) for the deployment.
 * `DNS_FRONTEND_URL_PREFIX`, prefix to prepend to the domain name for the *frontend* service (default: `choo`)
 * `SVC_FRONTEND_CONTAINER_PORT`, port number of the *frontend* service container image (default: 5000)
 * `SVC_FRONTEND_VCPU`, CPU units for the *frontend* service (default: 256, i.e. 0.25 VCPU)
 * `SVC_FRONTEND_RAM`, MiB RAM for the *frontend* service (default: 512)
 * `SVC_FRONTEND_INSTANCE_COUNT`, instance count behind the TLS enabled load balancer for the *frontend* service (default: 1)
 * `DNS_BACKEND_URL_PREFIX`, prefix to prepent to the domain name for the *backend* service (default: `api.choo`)
 * `SVC_BACKEND_CONTAINER_PORT`, same as above, but for the *backend* service... (default: 8080)
 * `SVC_BACKEND_VCPU`
 * `SVC_BACKEND_RAM`
 * `SVC_BACKEND_INSTANCE_COUNT`

After this, run `cdk deploy` and wait for the stack to properly be created, which will take a few minutes.

Note: When deploying for the first time, you may get an error, saying something along the lines of `This stack uses assets, so the toolkit stack must be deployed to the environment (Run "cdk bootstrap aws://AWS_ACCOUNT_ID/AWS_REGION")`, if you have not used CDK before on your account. If so, run the prompted command to bootstrap your account/region for using CDK.


## Other useful commands

 * `npm run test`, perform the jest unit tests
 * `cdk deploy`, deploy the stack as described above
 * `cdk diff`, compare deployed stack with current (edited) state
 * `cdk synth`, emits the synthesized CloudFormation template
