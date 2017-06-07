
import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Account} from "../shared/user/account.model";
import {BankAccount} from "../home/model/bank-account.model";
@Injectable()
export class BankAccountService{
    bankAccountByClient: BankAccount[];
    constructor(private http: Http) {
        console.log('BankAccountService initialized ....');
    }

    getBankAccountsByClient(account:Account){

        return this.http.get('/bank-account-api/accounts/'+'user')
            .map(res => res.json());
    }

    addBankAccount(bankAccount){
        return this.http.post('/bank-account-api/accounts',bankAccount);
    }

    addOperation(operation){
        console.log('sending post resquest');
        return this.http.post('/operation-api/operations',operation);
    }

    getOperationByAccountId(id:string){
        console.log('sending post resquest for cuurent account operations');
        return this.http.get('/operation-api/operations/'+id)
            .map(res => res.json());
    }

}
