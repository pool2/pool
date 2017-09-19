import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InventoryUsed } from './inventory-used.model';
import { InventoryUsedService } from './inventory-used.service';

@Component({
    selector: 'jhi-inventory-used-detail',
    templateUrl: './inventory-used-detail.component.html'
})
export class InventoryUsedDetailComponent implements OnInit, OnDestroy {

    inventoryUsed: InventoryUsed;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inventoryUsedService: InventoryUsedService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInventoryUseds();
    }

    load(id) {
        this.inventoryUsedService.find(id).subscribe((inventoryUsed) => {
            this.inventoryUsed = inventoryUsed;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInventoryUseds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inventoryUsedListModification',
            (response) => this.load(this.inventoryUsed.id)
        );
    }
}
