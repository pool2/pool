/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoolTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InventoryUsedDetailComponent } from '../../../../../../main/webapp/app/entities/inventory-used/inventory-used-detail.component';
import { InventoryUsedService } from '../../../../../../main/webapp/app/entities/inventory-used/inventory-used.service';
import { InventoryUsed } from '../../../../../../main/webapp/app/entities/inventory-used/inventory-used.model';

describe('Component Tests', () => {

    describe('InventoryUsed Management Detail Component', () => {
        let comp: InventoryUsedDetailComponent;
        let fixture: ComponentFixture<InventoryUsedDetailComponent>;
        let service: InventoryUsedService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoolTestModule],
                declarations: [InventoryUsedDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InventoryUsedService,
                    JhiEventManager
                ]
            }).overrideTemplate(InventoryUsedDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventoryUsedDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryUsedService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InventoryUsed(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inventoryUsed).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
