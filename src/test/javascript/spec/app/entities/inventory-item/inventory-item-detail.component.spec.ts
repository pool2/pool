/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoolTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InventoryItemDetailComponent } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item-detail.component';
import { InventoryItemService } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.service';
import { InventoryItem } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.model';

describe('Component Tests', () => {

    describe('InventoryItem Management Detail Component', () => {
        let comp: InventoryItemDetailComponent;
        let fixture: ComponentFixture<InventoryItemDetailComponent>;
        let service: InventoryItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoolTestModule],
                declarations: [InventoryItemDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InventoryItemService,
                    JhiEventManager
                ]
            }).overrideTemplate(InventoryItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventoryItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InventoryItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inventoryItem).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
