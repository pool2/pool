import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    WaterTestService,
    WaterTestPopupService,
    WaterTestComponent,
    WaterTestDetailComponent,
    WaterTestDialogComponent,
    WaterTestPopupComponent,
    WaterTestDeletePopupComponent,
    WaterTestDeleteDialogComponent,
    waterTestRoute,
    waterTestPopupRoute,
} from './';

const ENTITY_STATES = [
    ...waterTestRoute,
    ...waterTestPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WaterTestComponent,
        WaterTestDetailComponent,
        WaterTestDialogComponent,
        WaterTestDeleteDialogComponent,
        WaterTestPopupComponent,
        WaterTestDeletePopupComponent,
    ],
    entryComponents: [
        WaterTestComponent,
        WaterTestDialogComponent,
        WaterTestPopupComponent,
        WaterTestDeleteDialogComponent,
        WaterTestDeletePopupComponent,
    ],
    providers: [
        WaterTestService,
        WaterTestPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolWaterTestModule {}
