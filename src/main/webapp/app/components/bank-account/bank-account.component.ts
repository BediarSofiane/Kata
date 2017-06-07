import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {BankAccount} from "../../home/model/bank-account.model";
import {ActivatedRoute, Params} from "@angular/router";
import {BankAccountService} from "../../services/bank-account.service";
import {Location} from '@angular/common';
import {Operation} from "./model/operation.model";
import * as moment from 'moment/moment';

@Component({
  selector: 'jhi-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['bank-account.scss']
})
export class BankAccountComponent implements OnInit {



    @Input()
    bankAccount:BankAccount;
    operations:Operation[];
    amount:number=0.0;
  constructor(private activatedRoute:ActivatedRoute,
              private location:Location,
              private bankAccountService: BankAccountService) { }

  ngOnInit() {
      this.operations=[];
      console.log('#############################################" initialized bank-account component ')
      this.activatedRoute.params
          .switchMap((p:Params) => this.bankAccountService.bankAccountByClient.filter(account=>account.id==p['id']))
          .subscribe(account=>{
              this.bankAccount=account;
              this.getOperationByAccountId();
              console.log("bank account changed");
          });


  }

    onDeposit(){
      if(this.amount>0){

          this.createOperation(this.amount,'deposite');

      }

    }

    onWithdrawal(){
        if(this.amount>0) {
            this.createOperation(-this.amount,'withdrawal');
        }
    }
    createOperation(amount:number,message:string){
        if(amount!=0.0){
            console.log('------------------------------- creating new operation');
            this.bankAccountService.addOperation(new Operation(null,this.bankAccount,moment(),amount,message))
                .subscribe(()=>{
                    console.log('operation created successfully .... ');
                    this.getOperationByAccountId();
                },
                    ()=>{console.log('operation could not be created .... ')});
        }

        this.bankAccount.credit=+this.bankAccount.credit + +amount;
        console.log(this.operations);
    }

    getOperationByAccountId(){
        this.bankAccountService.getOperationByAccountId(this.bankAccount.id)
            .subscribe(response=>{
            this.operations=response as Operation[];
            console.log(this.operations);

        },()=> {console.log("error loading operations by account ID")});
    }

}
