import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterMyWikiSharedModule } from 'app/shared/shared.module';
import { CategoriesComponent } from './categories.component';
import { CategoriesDetailComponent } from './categories-detail.component';
import { CategoriesUpdateComponent } from './categories-update.component';
import { CategoriesDeleteDialogComponent } from './categories-delete-dialog.component';
import { categoriesRoute } from './categories.route';

@NgModule({
  imports: [JhipsterMyWikiSharedModule, RouterModule.forChild(categoriesRoute)],
  declarations: [CategoriesComponent, CategoriesDetailComponent, CategoriesUpdateComponent, CategoriesDeleteDialogComponent],
  entryComponents: [CategoriesDeleteDialogComponent],
})
export class JhipsterMyWikiCategoriesModule {}
