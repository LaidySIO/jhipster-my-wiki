import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICategories, Categories } from 'app/shared/model/categories.model';
import { CategoriesService } from './categories.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-categories-update',
  templateUrl: './categories-update.component.html',
})
export class CategoriesUpdateComponent implements OnInit {
  isSaving = false;
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    categoryName: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(25), Validators.pattern('^[a-zA-Z0-9]*$')]],
    description: [null, [Validators.required, Validators.minLength(10)]],
    categoryImage: [],
    categoryImageContentType: [],
    isPublic: [null, [Validators.required]],
    user: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected categoriesService: CategoriesService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categories }) => {
      this.updateForm(categories);
    });
  }

  updateForm(categories: ICategories): void {
    this.editForm.patchValue({
      id: categories.id,
      categoryName: categories.categoryName,
      description: categories.description,
      categoryImage: categories.categoryImage,
      categoryImageContentType: categories.categoryImageContentType,
      isPublic: categories.isPublic,
      user: categories.user,
      creationDate: categories.creationDate,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhipsterMyWikiApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categories = this.createFromForm();
    if (categories.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriesService.update(categories));
    } else {
      this.subscribeToSaveResponse(this.categoriesService.create(categories));
    }
  }

  private createFromForm(): ICategories {
    return {
      ...new Categories(),
      id: this.editForm.get(['id'])!.value,
      categoryName: this.editForm.get(['categoryName'])!.value,
      description: this.editForm.get(['description'])!.value,
      categoryImageContentType: this.editForm.get(['categoryImageContentType'])!.value,
      categoryImage: this.editForm.get(['categoryImage'])!.value,
      isPublic: this.editForm.get(['isPublic'])!.value,
      user: this.editForm.get(['user'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategories>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
