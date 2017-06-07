import {BankAccount} from "../../../home/model/bank-account.model";
import {Moment} from 'moment/moment';
export class Operation{
    constructor(
        public id:string,
      public bankAccount:BankAccount,
      public date:Moment,
      public amount:number,
      public description:string

    ){}
}
