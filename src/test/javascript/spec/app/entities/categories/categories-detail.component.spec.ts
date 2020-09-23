import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JhipsterMyWikiTestModule } from '../../../test.module';
import { CategoriesDetailComponent } from 'app/entities/categories/categories-detail.component';
import { Categories } from 'app/shared/model/categories.model';

describe('Component Tests', () => {
  describe('Categories Management Detail Component', () => {
    let comp: CategoriesDetailComponent;
    let fixture: ComponentFixture<CategoriesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categories: new Categories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterMyWikiTestModule],
        declarations: [CategoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
