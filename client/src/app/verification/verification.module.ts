import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VerificationRoutingModule } from './verification-routing.module';
import { EmailVerificationComponent } from './email-verification/email-verification.component';


@NgModule({
  declarations: [
    EmailVerificationComponent
  ],
  imports: [
    CommonModule,
    VerificationRoutingModule
  ]
})
export class VerificationModule { }
