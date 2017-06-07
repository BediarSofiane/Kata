import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { HomeComponent } from './';
import {BankAccountComponent} from "../components/bank-account/bank-account.component";

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    },
    children:[
        {
            path: 'account/:id',
            component: BankAccountComponent,

        }
    ]

};
