import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WaterTest } from './water-test.model';
import { WaterTestPopupService } from './water-test-popup.service';
import { WaterTestService } from './water-test.service';

@Component({
    selector: 'jhi-water-test-delete-dialog',
    templateUrl: './water-test-delete-dialog.component.html'
})
export class WaterTestDeleteDialogComponent {

    waterTest: WaterTest;

    constructor(
        private waterTestService: WaterTestService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.waterTestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'waterTestListModification',
                content: 'Deleted an waterTest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-water-test-delete-popup',
    template: ''
})
export class WaterTestDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private waterTestPopupService: WaterTestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.waterTestPopupService
                .open(WaterTestDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
