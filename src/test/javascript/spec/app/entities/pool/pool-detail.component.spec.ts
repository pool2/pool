/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoolTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PoolDetailComponent } from '../../../../../../main/webapp/app/entities/pool/pool-detail.component';
import { PoolService } from '../../../../../../main/webapp/app/entities/pool/pool.service';
import { Pool } from '../../../../../../main/webapp/app/entities/pool/pool.model';

describe('Component Tests', () => {

    describe('Pool Management Detail Component', () => {
        let comp: PoolDetailComponent;
        let fixture: ComponentFixture<PoolDetailComponent>;
        let service: PoolService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoolTestModule],
                declarations: [PoolDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PoolService,
                    JhiEventManager
                ]
            }).overrideTemplate(PoolDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoolDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoolService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pool(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pool).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
