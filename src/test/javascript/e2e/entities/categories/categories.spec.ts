import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategoriesComponentsPage, CategoriesDeleteDialog, CategoriesUpdatePage } from './categories.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Categories e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoriesComponentsPage: CategoriesComponentsPage;
  let categoriesUpdatePage: CategoriesUpdatePage;
  let categoriesDeleteDialog: CategoriesDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Categories', async () => {
    await navBarPage.goToEntity('categories');
    categoriesComponentsPage = new CategoriesComponentsPage();
    await browser.wait(ec.visibilityOf(categoriesComponentsPage.title), 5000);
    expect(await categoriesComponentsPage.getTitle()).to.eq('jhipsterMyWikiApp.categories.home.title');
    await browser.wait(ec.or(ec.visibilityOf(categoriesComponentsPage.entities), ec.visibilityOf(categoriesComponentsPage.noResult)), 1000);
  });

  it('should load create Categories page', async () => {
    await categoriesComponentsPage.clickOnCreateButton();
    categoriesUpdatePage = new CategoriesUpdatePage();
    expect(await categoriesUpdatePage.getPageTitle()).to.eq('jhipsterMyWikiApp.categories.home.createOrEditLabel');
    await categoriesUpdatePage.cancel();
  });

  it('should create and save Categories', async () => {
    const nbButtonsBeforeCreate = await categoriesComponentsPage.countDeleteButtons();

    await categoriesComponentsPage.clickOnCreateButton();

    await promise.all([
      categoriesUpdatePage.setCategoryNameInput('h'),
      categoriesUpdatePage.setDescriptionInput('description'),
      categoriesUpdatePage.setCategoryImageInput(absolutePath),
      categoriesUpdatePage.setUserInput('user'),
      categoriesUpdatePage.setCreationDateInput('2000-12-31'),
    ]);

    expect(await categoriesUpdatePage.getCategoryNameInput()).to.eq('h', 'Expected CategoryName value to be equals to h');
    expect(await categoriesUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await categoriesUpdatePage.getCategoryImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected CategoryImage value to be end with ' + fileNameToUpload
    );
    const selectedIsPublic = categoriesUpdatePage.getIsPublicInput();
    if (await selectedIsPublic.isSelected()) {
      await categoriesUpdatePage.getIsPublicInput().click();
      expect(await categoriesUpdatePage.getIsPublicInput().isSelected(), 'Expected isPublic not to be selected').to.be.false;
    } else {
      await categoriesUpdatePage.getIsPublicInput().click();
      expect(await categoriesUpdatePage.getIsPublicInput().isSelected(), 'Expected isPublic to be selected').to.be.true;
    }
    expect(await categoriesUpdatePage.getUserInput()).to.eq('user', 'Expected User value to be equals to user');
    expect(await categoriesUpdatePage.getCreationDateInput()).to.eq('2000-12-31', 'Expected creationDate value to be equals to 2000-12-31');

    await categoriesUpdatePage.save();
    expect(await categoriesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await categoriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Categories', async () => {
    const nbButtonsBeforeDelete = await categoriesComponentsPage.countDeleteButtons();
    await categoriesComponentsPage.clickOnLastDeleteButton();

    categoriesDeleteDialog = new CategoriesDeleteDialog();
    expect(await categoriesDeleteDialog.getDialogTitle()).to.eq('jhipsterMyWikiApp.categories.delete.question');
    await categoriesDeleteDialog.clickOnConfirmButton();

    expect(await categoriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
