import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { BankSharedModule, UserRouteAccessService } from './shared';
import { BankHomeModule } from './home/home.module';
import { BankAdminModule } from './admin/admin.module';
import { BankAccountModule } from './account/account.module';
import { BankEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import { BankAccountComponent } from './components/bank-account/bank-account.component';
import {BankAccountService} from "./services/bank-account.service";
import {AccountBankModule} from "./components/bank-account/index";

@NgModule({
    imports: [
        BankHomeModule,
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        BankSharedModule,
        BankAdminModule,
        BankAccountModule,
        BankEntityModule,

    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,

    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        BankAccountService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class BankAppModule {}
