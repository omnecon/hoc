/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { DataImportMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix-delete-dialog.component';
import { DataImportMySuffixService } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.service';

describe('Component Tests', () => {

    describe('DataImportMySuffix Management Delete Component', () => {
        let comp: DataImportMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<DataImportMySuffixDeleteDialogComponent>;
        let service: DataImportMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [DataImportMySuffixDeleteDialogComponent],
                providers: [
                    DataImportMySuffixService
                ]
            })
            .overrideTemplate(DataImportMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataImportMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataImportMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
