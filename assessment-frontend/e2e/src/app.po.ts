import { browser, by, element } from 'protractor';

export class AppPage {
  async navigateTo(): Promise<unknown> {
    return browser.get(browser.baseUrl);
  }

  async getCalculationText(): Promise<String> {
    return element(by.css('app-root app-calculator #calculation')).getText();
  }

  async getButton(id: string): Promise<HTMLElement> {
    return element(by.css(`app-root app-calculator #button-${id}`));
  }
}
