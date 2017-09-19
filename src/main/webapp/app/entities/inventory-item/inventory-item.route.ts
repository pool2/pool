import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InventoryItemComponent } from './inventory-item.component';
import { InventoryItemDetailComponent } from './inventory-item-detail.component';
import { InventoryItemPopupComponent } from './inventory-item-dialog.component';
import { InventoryItemDeletePopupComponent } from './inventory-item-delete-dialog.component';

export const inventoryItemRoute: Routes = [
    {
        path: 'inventory-item',
        component: InventoryItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryItems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inventory-item/:id',
        component: InventoryItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryItemPopupRoute: Routes = [
    {
        path: 'inventory-item-new',
        component: InventoryItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventory-item/:id/edit',
        component: InventoryItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventory-item/:id/delete',
        component: InventoryItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
