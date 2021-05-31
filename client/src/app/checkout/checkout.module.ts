import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CheckoutRoutingModule } from './checkout-routing.module';
import { CheckoutComponent } from './checkout.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { PaymentComponent } from './payment/payment.component';
import { PersonalComponent } from './personal/personal.component';
import { ProgressBarComponent } from './progress-bar/progress-bar.component';
import { ShippingComponent } from './shipping/shipping.component';
import { SuccessComponent } from './success/success.component';
import { SummaryComponent } from './summary/summary.component';
import { BankAcceptComponent } from './summary/bank-accept/bank-accept.component';


@NgModule({
  declarations: [
    CheckoutComponent,
    ConfirmationComponent,
    PaymentComponent,
    PersonalComponent,
    ProgressBarComponent,
    ShippingComponent,
    SuccessComponent,
    SummaryComponent,
    BankAcceptComponent
  ],
  imports: [
    CommonModule,
    CheckoutRoutingModule
  ]
})
export class CheckoutModule { }
