import {Component, Input} from '@angular/core';
import {ChatMessageModel} from "../../models/chat-message.model";

@Component({
  selector: 'app-chat-message',
  templateUrl: './chat-message.component.html',
  styleUrls: ['./chat-message.component.scss']
})
export class ChatMessageComponent {
  @Input()
  message!: ChatMessageModel;
}
