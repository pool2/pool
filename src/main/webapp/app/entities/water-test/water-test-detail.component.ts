import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { WaterTest } from './water-test.model';
import { WaterTestService } from './water-test.service';

@Component({
    selector: 'jhi-water-test-detail',
    templateUrl: './water-test-detail.component.html'
})
export class WaterTestDetailComponent implements OnInit, OnDestroy {

    waterTest: WaterTest;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private waterTestService: WaterTestService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWaterTests();
    }

    load(id) {
        this.waterTestService.find(id).subscribe((waterTest) => {
            this.waterTest = waterTest;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWaterTests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'waterTestListModification',
            (response) => this.load(this.waterTest.id)
        );
    }
}
