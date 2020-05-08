import { element, by, ElementFinder } from 'protractor';

export class PartieComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-partie div table .btn-danger'));
  title = element.all(by.css('jhi-partie div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class PartieUpdatePage {
  pageTitle = element(by.id('jhi-partie-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  intituleInput = element(by.id('field_intitule'));

  joueurSelect = element(by.id('field_joueur'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIntituleInput(intitule: string): Promise<void> {
    await this.intituleInput.sendKeys(intitule);
  }

  async getIntituleInput(): Promise<string> {
    return await this.intituleInput.getAttribute('value');
  }

  async joueurSelectLastOption(): Promise<void> {
    await this.joueurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async joueurSelectOption(option: string): Promise<void> {
    await this.joueurSelect.sendKeys(option);
  }

  getJoueurSelect(): ElementFinder {
    return this.joueurSelect;
  }

  async getJoueurSelectedOption(): Promise<string> {
    return await this.joueurSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class PartieDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-partie-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-partie'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
