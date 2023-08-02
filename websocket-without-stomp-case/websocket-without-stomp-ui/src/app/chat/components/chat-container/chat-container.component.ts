import {Component} from '@angular/core';
import {ChatMessageModel} from "../../models/chat-message.model";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-chat-container',
  templateUrl: './chat-container.component.html',
  styleUrls: ['./chat-container.component.scss']
})
export class ChatContainerComponent {
  readonly messages: ChatMessageModel[] = [];
  connected: boolean = false;
  websocket?: WebSocket;

  chatFormGroup: FormGroup = this.formBuilder.group({
    name: new FormControl(''),
    text: new FormControl('')
  });

  constructor(private formBuilder: FormBuilder) {
  }

  connect(): void {
    if (this.connected) {
      return;
    }

    this.websocket = new WebSocket(environment.BASE_CHAT_URI);

    this.websocket.onopen = (ev: Event) => {
      console.log(ev);
      this.connected = true;
    };

    this.websocket.onerror = (ev: Event) => {
      console.error(ev);
      this.connected = false;
    };

    this.websocket.onmessage = (data: MessageEvent) => {
      this.messages.push(JSON.parse(data.data));
    };

    this.websocket.onclose = (ev: CloseEvent) => {
      this.connected = false;
    };
  }

  disconnect(): void {
    if (!this.connected) {
      return;
    }

    if (this.websocket) {
      this.websocket.close();
      this.websocket = undefined;
      this.connected = false;
    }
  }

  sendMessage(): void {
    if (!this.websocket) {
      return;
    }

    this.websocket.send(JSON.stringify(this.chatFormGroup.value));
    this.chatFormGroup.patchValue({
      text: ''
    });
  }
}
