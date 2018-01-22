/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { DataImportMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix-dialog.component';
import { DataImportMySuffixService } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.service';
import { DataImportMySuffix } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.model';

describe('Component Tests', () => {

    describe('DataImportMySuffix Management Dialog Component', () => {
        let comp: DataImportMySuffixDialogComponent;
        let fixture: ComponentFixture<DataImportMySuffixDialogComponent>;
        let service: DataImportMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [DataImportMySuffixDialogComponent],
                providers: [
                    DataImportMySuffixService
                ]
            })
            .overrideTemplate(DataImportMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataImportMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataImportMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DataImportMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.dataImport = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dataImportListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DataImportMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.dataImport = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dataImportListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
