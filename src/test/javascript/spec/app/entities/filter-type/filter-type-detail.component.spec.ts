/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoolTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FilterTypeDetailComponent } from '../../../../../../main/webapp/app/entities/filter-type/filter-type-detail.component';
import { FilterTypeService } from '../../../../../../main/webapp/app/entities/filter-type/filter-type.service';
import { FilterType } from '../../../../../../main/webapp/app/entities/filter-type/filter-type.model';

describe('Component Tests', () => {

    describe('FilterType Management Detail Component', () => {
        let comp: FilterTypeDetailComponent;
        let fixture: ComponentFixture<FilterTypeDetailComponent>;
        let service: FilterTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoolTestModule],
                declarations: [FilterTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FilterTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(FilterTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilterTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilterTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FilterType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.filterType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
