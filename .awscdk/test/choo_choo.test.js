const { expect, matchTemplate, MatchStyle } = require('@aws-cdk/assert');
const cdk = require('@aws-cdk/core');
const ChooChoo = require('../lib/choo_choo-stack');

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new ChooChoo.ChooChooStack(app, 'MyTestStack');
    // THEN
    expect(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});