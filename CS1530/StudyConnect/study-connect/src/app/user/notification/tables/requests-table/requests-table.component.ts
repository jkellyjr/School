import { Component, OnInit, Input } from '@angular/core';
import {MatTableDataSource, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { RequestContact } from '../../../../library/objects/index';
import { UserService} from '../../../user.service';

@Component({
  selector: 'requests-table',
  templateUrl: './requests-table.component.html',
  styleUrls: ['./requests-table.component.css']
})
export class RequestsTableComponent implements OnInit {
  @Input()
  tempOne: RequestContact[];

  @Input()
  tempTwo: RequestContact[];

  @Input()
  userId:number;

  requests: RequestContact[];

  displayedColumns = ['sender', 'message', 'accept', 'decline'];
  constructor(private service:UserService) {
    this.requests = new Array<RequestContact>();
  }

  ngOnInit() {
    for(let i=0;i<this.tempOne.length;i++){
      if(this.tempOne[i].requestor_id != this.userId){
        this.requests.push(this.tempOne[i]);
      }
    }

    for(let i=0;i<this.tempTwo.length;i++){
      if(this.tempTwo[i].requestor_id != this.userId){
        this.requests.push(this.tempTwo[i]);
      }
    }
  }

  accept(id:number): void {
    console.log("Accept ID"+id);
    this.service.respondContactRequest(id,'true');
  }

  decline(id:number): void {
    console.log("decline ID"+id);
    this.service.respondContactRequest(id,'false');
  }

}
