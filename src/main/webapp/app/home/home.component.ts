import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {BankAccountService} from "../services/bank-account.service";
import {BankAccount} from "./model/bank-account.model";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    bankAccount:BankAccount[]=[];
    selectedAccountID:number=0;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: EventManager,
        private bankAccountService: BankAccountService
    ) {
    }

    ngOnInit() {
        console.log("ngOnInit")
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.getBankAccounts();

        this.registerAuthenticationSuccess();
        this.getBankAccounts();



    }
    getBankAccounts(){
        this.bankAccountService.getBankAccountsByClient(this.account)
            .subscribe(response=>{
                this.bankAccount=response as BankAccount[];
                this.bankAccountService.bankAccountByClient=this.bankAccount;

                if(this.bankAccount.length<2){

                    this.addBankAccount(new BankAccount(null,550.5,"Account "+this.bankAccount.length,new Account(true,['ROLE_USER'],"user@user","user","en","user","user","")));
                }

            });


    }

    addBankAccount(bankAccount:BankAccount){
        this.bankAccountService.addBankAccount(bankAccount).subscribe(
            ()=>{console.log('bankAccount created successfully .... ')},
            ()=>{console.log('bankAccount could not be created .... ')});

    }


    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;

            });

        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
