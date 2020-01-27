#!/usr/bin/env node

const cdk = require('@aws-cdk/core');
const { ChooChooStack } = require('../lib/choo_choo-stack');

const app = new cdk.App();
new ChooChooStack(app, 'ChooChooStack');
