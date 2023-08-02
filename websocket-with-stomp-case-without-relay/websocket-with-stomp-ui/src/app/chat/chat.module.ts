import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ChatMessageComponent} from './components/chat-message/chat-message.component';
import {ChatContainerComponent} from './components/chat-container/chat-container.component';
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {ChatRoutingModule} from "./chat-routing.module";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {RxStompService} from "./services/rx-stomp.service";
import {rxStompServiceFactory} from "./services/rx-stomp.factory";

@NgModule({
  declarations: [
    ChatMessageComponent,
    ChatContainerComponent
  ],
  imports: [
    CommonModule,
    ChatRoutingModule,

    MatInputModule,
    ReactiveFormsModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatListModule
  ],
  providers: [
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
    },
  ],
})
export class ChatModule {
}
