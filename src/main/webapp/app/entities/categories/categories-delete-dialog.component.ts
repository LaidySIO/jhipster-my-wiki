import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategories } from 'app/shared/model/categories.model';
import { CategoriesService } from './categories.service';

@Component({
  templateUrl: './categories-delete-dialog.component.html',
})
export class CategoriesDeleteDialogComponent {
  categories?: ICategories;

  constructor(
    protected categoriesService: CategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriesListModification');
      this.activeModal.close();
    });
  }
}
