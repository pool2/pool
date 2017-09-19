import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    InventoryItemService,
    InventoryItemPopupService,
    InventoryItemComponent,
    InventoryItemDetailComponent,
    InventoryItemDialogComponent,
    InventoryItemPopupComponent,
    InventoryItemDeletePopupComponent,
    InventoryItemDeleteDialogComponent,
    inventoryItemRoute,
    inventoryItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inventoryItemRoute,
    ...inventoryItemPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InventoryItemComponent,
        InventoryItemDetailComponent,
        InventoryItemDialogComponent,
        InventoryItemDeleteDialogComponent,
        InventoryItemPopupComponent,
        InventoryItemDeletePopupComponent,
    ],
    entryComponents: [
        InventoryItemComponent,
        InventoryItemDialogComponent,
        InventoryItemPopupComponent,
        InventoryItemDeleteDialogComponent,
        InventoryItemDeletePopupComponent,
    ],
    providers: [
        InventoryItemService,
        InventoryItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolInventoryItemModule {}
