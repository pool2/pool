/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoolTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FilterBrandDetailComponent } from '../../../../../../main/webapp/app/entities/filter-brand/filter-brand-detail.component';
import { FilterBrandService } from '../../../../../../main/webapp/app/entities/filter-brand/filter-brand.service';
import { FilterBrand } from '../../../../../../main/webapp/app/entities/filter-brand/filter-brand.model';

describe('Component Tests', () => {

    describe('FilterBrand Management Detail Component', () => {
        let comp: FilterBrandDetailComponent;
        let fixture: ComponentFixture<FilterBrandDetailComponent>;
        let service: FilterBrandService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoolTestModule],
                declarations: [FilterBrandDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FilterBrandService,
                    JhiEventManager
                ]
            }).overrideTemplate(FilterBrandDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilterBrandDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilterBrandService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FilterBrand(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.filterBrand).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
