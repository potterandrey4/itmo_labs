import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FiltersPanelComponent } from './components/filters-panel/filters-panel.component';
import { LabworkTableComponent } from './components/labwork-table/labwork-table.component';
import { PaginationControlsComponent } from './components/pagination-controls/pagination-controls.component';
import { LabworkFormComponent } from './components/labwork-form/labwork-form.component';
import { BarsActionsComponent } from './components/bars-actions/bars-actions.component';
import { DisciplineManagerComponent } from './components/discipline-manager/discipline-manager.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';

@NgModule({
  declarations: [
    AppComponent,
    FiltersPanelComponent,
    LabworkTableComponent,
    PaginationControlsComponent,
    LabworkFormComponent,
    BarsActionsComponent,
    DisciplineManagerComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatSelectModule,
    MatSelectModule,
    MatToolbarModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule
    , MatFormFieldModule
    , MatInputModule
    , MatCardModule
    , MatTableModule
    , MatPaginatorModule
    , MatSnackBarModule
    , MatListModule
    , MatSlideToggleModule
    , MatExpansionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
