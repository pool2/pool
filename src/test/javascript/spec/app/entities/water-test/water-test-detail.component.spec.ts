/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoolTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WaterTestDetailComponent } from '../../../../../../main/webapp/app/entities/water-test/water-test-detail.component';
import { WaterTestService } from '../../../../../../main/webapp/app/entities/water-test/water-test.service';
import { WaterTest } from '../../../../../../main/webapp/app/entities/water-test/water-test.model';

describe('Component Tests', () => {

    describe('WaterTest Management Detail Component', () => {
        let comp: WaterTestDetailComponent;
        let fixture: ComponentFixture<WaterTestDetailComponent>;
        let service: WaterTestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoolTestModule],
                declarations: [WaterTestDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WaterTestService,
                    JhiEventManager
                ]
            }).overrideTemplate(WaterTestDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WaterTestDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WaterTestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WaterTest(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.waterTest).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
