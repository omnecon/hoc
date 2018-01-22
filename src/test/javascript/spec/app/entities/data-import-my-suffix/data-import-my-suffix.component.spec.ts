/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { DataImportMySuffixComponent } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.component';
import { DataImportMySuffixService } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.service';
import { DataImportMySuffix } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.model';

describe('Component Tests', () => {

    describe('DataImportMySuffix Management Component', () => {
        let comp: DataImportMySuffixComponent;
        let fixture: ComponentFixture<DataImportMySuffixComponent>;
        let service: DataImportMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [DataImportMySuffixComponent],
                providers: [
                    DataImportMySuffixService
                ]
            })
            .overrideTemplate(DataImportMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataImportMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataImportMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DataImportMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dataImports[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
