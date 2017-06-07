import {Account} from "../../shared/user/account.model";
export class BankAccount{
    constructor(
        public id:string,
        public credit: number,
        public alias:string,
        public client:Account


    ){}
}
