import { AppPage } from './app.po';
import { browser, logging } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display 0', async () => {
    await page.navigateTo();
    expect(await page.getCalculationText()).toEqual('0');
  });

  it('should be able to add', async () => {
    (await page.getButton('3')).click();
    (await page.getButton('add')).click();
    (await page.getButton('3')).click();
    (await page.getButton('calc')).click();

    await browser.sleep(1000);

    expect(await page.getCalculationText()).toEqual('6');
  });

  it('should be able to subtract', async () => {
    (await page.getButton('subtract')).click();
    (await page.getButton('3')).click();
    (await page.getButton('calc')).click();

    await browser.sleep(1000);

    expect(await page.getCalculationText()).toEqual('3');
  });

  it('should be able to multiply', async () => {
    (await page.getButton('multiply')).click();
    (await page.getButton('3')).click();
    (await page.getButton('calc')).click();

    await browser.sleep(1000);

    expect(await page.getCalculationText()).toEqual('9');
  });

  it('should be able to divide', async () => {
    (await page.getButton('divide')).click();
    (await page.getButton('3')).click();
    (await page.getButton('calc')).click();

    await browser.sleep(1000);

    expect(await page.getCalculationText()).toEqual('3');
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
