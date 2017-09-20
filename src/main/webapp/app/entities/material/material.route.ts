import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MaterialComponent } from './material.component';
import { MaterialDetailComponent } from './material-detail.component';
import { MaterialPopupComponent } from './material-dialog.component';
import { MaterialDeletePopupComponent } from './material-delete-dialog.component';

export const materialRoute: Routes = [
    {
        path: 'material',
        component: MaterialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Materials'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'material/:id',
        component: MaterialDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Materials'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materialPopupRoute: Routes = [
    {
        path: 'material-new',
        component: MaterialPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Materials'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material/:id/edit',
        component: MaterialPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Materials'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material/:id/delete',
        component: MaterialDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Materials'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
