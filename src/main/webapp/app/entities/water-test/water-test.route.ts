import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WaterTestComponent } from './water-test.component';
import { WaterTestDetailComponent } from './water-test-detail.component';
import { WaterTestPopupComponent } from './water-test-dialog.component';
import { WaterTestDeletePopupComponent } from './water-test-delete-dialog.component';

export const waterTestRoute: Routes = [
    {
        path: 'water-test',
        component: WaterTestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WaterTests'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'water-test/:id',
        component: WaterTestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WaterTests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const waterTestPopupRoute: Routes = [
    {
        path: 'water-test-new',
        component: WaterTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WaterTests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'water-test/:id/edit',
        component: WaterTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WaterTests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'water-test/:id/delete',
        component: WaterTestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WaterTests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
