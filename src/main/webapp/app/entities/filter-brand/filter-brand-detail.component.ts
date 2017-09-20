import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FilterBrand } from './filter-brand.model';
import { FilterBrandService } from './filter-brand.service';

@Component({
    selector: 'jhi-filter-brand-detail',
    templateUrl: './filter-brand-detail.component.html'
})
export class FilterBrandDetailComponent implements OnInit, OnDestroy {

    filterBrand: FilterBrand;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private filterBrandService: FilterBrandService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilterBrands();
    }

    load(id) {
        this.filterBrandService.find(id).subscribe((filterBrand) => {
            this.filterBrand = filterBrand;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilterBrands() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filterBrandListModification',
            (response) => this.load(this.filterBrand.id)
        );
    }
}
