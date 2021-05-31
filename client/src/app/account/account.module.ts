import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { AccountComponent } from './account.component';
import { AddressComponent } from './address/address.component';
import { HelpComponent } from './help/help.component';
import { ListOrdersComponent } from './list-orders/list-orders.component';
import { InformationComponent } from './information/information.component';
import { ResetDetailsComponent } from './information/reset-details/reset-details.component';


@NgModule({
  declarations: [
    AccountComponent,
    AddressComponent,
    HelpComponent,
    ListOrdersComponent,
    InformationComponent,
    ResetDetailsComponent
  ],
  imports: [
    CommonModule,
    AccountRoutingModule
  ]
})
export class AccountModule { }
