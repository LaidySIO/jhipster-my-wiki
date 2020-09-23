import { element, by, ElementFinder } from 'protractor';

export class CategoriesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-categories div table .btn-danger'));
  title = element.all(by.css('jhi-categories div h2#page-heading span')).first();
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

export class CategoriesUpdatePage {
  pageTitle = element(by.id('jhi-categories-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  categoryNameInput = element(by.id('field_categoryName'));
  descriptionInput = element(by.id('field_description'));
  categoryImageInput = element(by.id('file_categoryImage'));
  isPublicInput = element(by.id('field_isPublic'));
  userInput = element(by.id('field_user'));
  creationDateInput = element(by.id('field_creationDate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCategoryNameInput(categoryName: string): Promise<void> {
    await this.categoryNameInput.sendKeys(categoryName);
  }

  async getCategoryNameInput(): Promise<string> {
    return await this.categoryNameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setCategoryImageInput(categoryImage: string): Promise<void> {
    await this.categoryImageInput.sendKeys(categoryImage);
  }

  async getCategoryImageInput(): Promise<string> {
    return await this.categoryImageInput.getAttribute('value');
  }

  getIsPublicInput(): ElementFinder {
    return this.isPublicInput;
  }

  async setUserInput(user: string): Promise<void> {
    await this.userInput.sendKeys(user);
  }

  async getUserInput(): Promise<string> {
    return await this.userInput.getAttribute('value');
  }

  async setCreationDateInput(creationDate: string): Promise<void> {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput(): Promise<string> {
    return await this.creationDateInput.getAttribute('value');
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

export class CategoriesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-categories-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-categories'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
