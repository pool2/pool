import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FilterType } from './filter-type.model';
import { FilterTypeService } from './filter-type.service';

@Component({
    selector: 'jhi-filter-type-detail',
    templateUrl: './filter-type-detail.component.html'
})
export class FilterTypeDetailComponent implements OnInit, OnDestroy {

    filterType: FilterType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private filterTypeService: FilterTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilterTypes();
    }

    load(id) {
        this.filterTypeService.find(id).subscribe((filterType) => {
            this.filterType = filterType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilterTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filterTypeListModification',
            (response) => this.load(this.filterType.id)
        );
    }
}
