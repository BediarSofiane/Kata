import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankSharedModule } from '../../shared';

import {BankAccountService} from "../../services/bank-account.service";
import {BankAccountComponent} from "./bank-account.component";

@NgModule({
    imports: [
        BankSharedModule,
    ],
    declarations: [
        BankAccountComponent,
    ],
    entryComponents: [
    ],
    providers: [
        BankAccountService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountBankModule {}
