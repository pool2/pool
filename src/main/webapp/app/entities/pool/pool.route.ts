import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PoolComponent } from './pool.component';
import { PoolDetailComponent } from './pool-detail.component';
import { PoolPopupComponent } from './pool-dialog.component';
import { PoolDeletePopupComponent } from './pool-delete-dialog.component';

export const poolRoute: Routes = [
    {
        path: 'pool',
        component: PoolComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pools'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pool/:id',
        component: PoolDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pools'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poolPopupRoute: Routes = [
    {
        path: 'pool-new',
        component: PoolPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pool/:id/edit',
        component: PoolPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pool/:id/delete',
        component: PoolDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
