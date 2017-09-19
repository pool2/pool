import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    InventoryUsedService,
    InventoryUsedPopupService,
    InventoryUsedComponent,
    InventoryUsedDetailComponent,
    InventoryUsedDialogComponent,
    InventoryUsedPopupComponent,
    InventoryUsedDeletePopupComponent,
    InventoryUsedDeleteDialogComponent,
    inventoryUsedRoute,
    inventoryUsedPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inventoryUsedRoute,
    ...inventoryUsedPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InventoryUsedComponent,
        InventoryUsedDetailComponent,
        InventoryUsedDialogComponent,
        InventoryUsedDeleteDialogComponent,
        InventoryUsedPopupComponent,
        InventoryUsedDeletePopupComponent,
    ],
    entryComponents: [
        InventoryUsedComponent,
        InventoryUsedDialogComponent,
        InventoryUsedPopupComponent,
        InventoryUsedDeleteDialogComponent,
        InventoryUsedDeletePopupComponent,
    ],
    providers: [
        InventoryUsedService,
        InventoryUsedPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolInventoryUsedModule {}
