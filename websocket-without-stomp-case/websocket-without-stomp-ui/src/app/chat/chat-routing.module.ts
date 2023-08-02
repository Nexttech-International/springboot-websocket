import {RouterModule, Routes} from "@angular/router";
import {ChatContainerComponent} from "./components/chat-container/chat-container.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
  {
    path: 'chat',
    component: ChatContainerComponent,
  },
  {
    path: '',
    redirectTo: '/chat',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChatRoutingModule {
}
