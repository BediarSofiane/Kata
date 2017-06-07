import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {BankAccountService} from "../services/bank-account.service";
import {AccountBankModule} from "../components/bank-account/bank-account.module";

@NgModule({
    imports: [
        BankSharedModule,
        RouterModule.forChild([ HOME_ROUTE ]),
        AccountBankModule
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [

    ],
    exports:[
        RouterModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BankHomeModule {}
