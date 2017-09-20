import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FilterBrandComponent } from './filter-brand.component';
import { FilterBrandDetailComponent } from './filter-brand-detail.component';
import { FilterBrandPopupComponent } from './filter-brand-dialog.component';
import { FilterBrandDeletePopupComponent } from './filter-brand-delete-dialog.component';

export const filterBrandRoute: Routes = [
    {
        path: 'filter-brand',
        component: FilterBrandComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterBrands'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'filter-brand/:id',
        component: FilterBrandDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterBrands'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filterBrandPopupRoute: Routes = [
    {
        path: 'filter-brand-new',
        component: FilterBrandPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterBrands'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filter-brand/:id/edit',
        component: FilterBrandPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterBrands'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filter-brand/:id/delete',
        component: FilterBrandDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilterBrands'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
