import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FilterTypeComponent } from './filter-type.component';
import { FilterTypeDetailComponent } from './filter-type-detail.component';
import { FilterTypePopupComponent } from './filter-type-dialog.component';
import { FilterTypeDeletePopupComponent } from './filter-type-delete-dialog.component';

export const filterTypeRoute: Routes = [
    {
        path: 'filter-type',
        component: FilterTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'filter-type/:id',
        component: FilterTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filterTypePopupRoute: Routes = [
    {
        path: 'filter-type-new',
        component: FilterTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filter-type/:id/edit',
        component: FilterTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filter-type/:id/delete',
        component: FilterTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
