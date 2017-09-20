import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FilterBrand } from './filter-brand.model';
import { FilterBrandPopupService } from './filter-brand-popup.service';
import { FilterBrandService } from './filter-brand.service';

@Component({
    selector: 'jhi-filter-brand-delete-dialog',
    templateUrl: './filter-brand-delete-dialog.component.html'
})
export class FilterBrandDeleteDialogComponent {

    filterBrand: FilterBrand;

    constructor(
        private filterBrandService: FilterBrandService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filterBrandService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filterBrandListModification',
                content: 'Deleted an filterBrand'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filter-brand-delete-popup',
    template: ''
})
export class FilterBrandDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filterBrandPopupService: FilterBrandPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.filterBrandPopupService
                .open(FilterBrandDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
