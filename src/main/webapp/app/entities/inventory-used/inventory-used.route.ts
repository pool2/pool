import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InventoryUsedComponent } from './inventory-used.component';
import { InventoryUsedDetailComponent } from './inventory-used-detail.component';
import { InventoryUsedPopupComponent } from './inventory-used-dialog.component';
import { InventoryUsedDeletePopupComponent } from './inventory-used-delete-dialog.component';

export const inventoryUsedRoute: Routes = [
    {
        path: 'inventory-used',
        component: InventoryUsedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryUseds'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inventory-used/:id',
        component: InventoryUsedDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryUseds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryUsedPopupRoute: Routes = [
    {
        path: 'inventory-used-new',
        component: InventoryUsedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryUseds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventory-used/:id/edit',
        component: InventoryUsedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryUseds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventory-used/:id/delete',
        component: InventoryUsedDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryUseds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
