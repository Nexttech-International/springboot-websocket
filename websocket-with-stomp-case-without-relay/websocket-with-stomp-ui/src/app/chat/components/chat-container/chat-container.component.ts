import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatMessageModel} from "../../models/chat-message.model";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {RxStompService} from "../../services/rx-stomp.service";
import {Subscription} from "rxjs";
import {IMessage, RxStompState} from "@stomp/rx-stomp";
import {stompConfig} from "../../services/rx-stomp.factory";

@Component({
  selector: 'app-chat-container',
  templateUrl: './chat-container.component.html',
  styleUrls: ['./chat-container.component.scss']
})
export class ChatContainerComponent implements OnInit, OnDestroy {
  readonly messages: ChatMessageModel[] = [];
  connected: boolean = false;
  clientId: string = '';

  private topicSubscription!: Subscription;

  chatFormGroup: FormGroup = this.formBuilder.group({
    name: new FormControl(''),
    text: new FormControl('')
  });

  constructor(private formBuilder: FormBuilder, private rxStompService: RxStompService) {
  }

  ngOnInit() {
    this.rxStompService.connected$
      .subscribe(value => this.connected = value === RxStompState.OPEN);
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

  connect(): void {
    if (this.connected) {
      console.log("Already connected...");
      return;
    }

    this.clientId = `client-id_${Math.random()}`;
    this.rxStompService.configure({
      ...stompConfig,
      connectHeaders: {
        ...stompConfig.connectHeaders,
        "client-id": this.clientId,
      }
    })
    this.rxStompService.activate();
  }

  disconnect(): void {
    if (!this.connected) {
      return;
    }

    this.rxStompService.deactivate();
    this.connected = false;

    if (this.topicSubscription) {
      this.topicSubscription.unsubscribe();
    }
  }

  subscribe(): void {
    if (this.topicSubscription) { // unsubscribe from old topic
      this.topicSubscription.unsubscribe();
    }

    this.topicSubscription = this.rxStompService
      .watch('/topic/chat', {"activemq.subscriptionName": this.clientId/*stompConfig.connectHeaders?.['client-id']*/ ?? ''})
      .subscribe((message: IMessage) => {
        this.messages.push(JSON.parse(message.body));
      });
  }

  sendMessage() {
    if (!this.topicSubscription) {
      console.log("No subscription found..")
      return;
    }

    this.rxStompService.publish({ destination: '/topic/chat', body: JSON.stringify(this.chatFormGroup.value) });

    // using Annotation Approach: /app/using-annotation => /topic/chat
    // this.rxStompService.publish({ destination: '/app/using-annotation', body: JSON.stringify(this.chatFormGroup.value) });
    this.chatFormGroup.patchValue({
      text: ''
    });
  }
}
